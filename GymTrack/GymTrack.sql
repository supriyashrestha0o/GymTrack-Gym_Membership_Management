USE gymtrack;
-- TABLES
CREATE TABLE IF NOT EXISTS Payments (
    payment_id   INT PRIMARY KEY AUTO_INCREMENT,
    member_id    INT NOT NULL,
    amount       DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status       ENUM('Paid','Refunded','Pending') DEFAULT 'Pending',
    FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PaymentLog (
    log_id      INT PRIMARY KEY AUTO_INCREMENT,
    payment_id  INT,
    old_status  VARCHAR(20),
    new_status  VARCHAR(20),
    changed_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TRIGGERS

DELIMITER $$
CREATE TRIGGER validate_payment
BEFORE INSERT ON Payments
FOR EACH ROW
BEGIN
    IF NEW.amount <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Payment amount must be greater than 0!';
    END IF;
    IF NOT EXISTS (
        SELECT 1 FROM Members WHERE member_id = NEW.member_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Member does not exist!';
    END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER log_payment_status
AFTER UPDATE ON Payments
FOR EACH ROW
BEGIN
    IF OLD.status != NEW.status THEN
        INSERT INTO PaymentLog (payment_id, old_status, new_status)
        VALUES (OLD.payment_id, OLD.status, NEW.status);
    END IF;
END $$
DELIMITER ;

-- STORED PROCEDURES

DELIMITER $$
CREATE PROCEDURE addMember(
    IN p_username  VARCHAR(50),
    IN p_password  VARCHAR(50),
    IN p_full_name VARCHAR(100),
    IN p_email     VARCHAR(100),
    IN p_phone     VARCHAR(15),
    IN p_plan_type ENUM('Normal','Premium'),
    IN p_duration  INT
)
BEGIN
    DECLARE v_user_id   INT;
    DECLARE v_member_id INT;
    DECLARE v_amount    DECIMAL(10,2);

    IF p_plan_type = 'Premium' THEN
        SET v_amount = p_duration * 3000;
    ELSE
        SET v_amount = p_duration * 1500;
    END IF;

    START TRANSACTION;

    INSERT INTO Users (username, password, role)
    VALUES (p_username, p_password, 'Member');
    SET v_user_id = LAST_INSERT_ID();

    INSERT INTO Members (user_id, full_name, email, phone)
    VALUES (v_user_id, p_full_name, p_email, p_phone);
    SET v_member_id = LAST_INSERT_ID();

    INSERT INTO MembershipPlans
        (member_id, plan_type, duration, start_date, total_amount)
    VALUES
        (v_member_id, p_plan_type, p_duration, CURDATE(), v_amount);

    COMMIT;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE updateMembership(
    IN p_member_id INT,
    IN p_new_plan  ENUM('Normal','Premium'),
    IN p_duration  INT
)
BEGIN
    DECLARE v_amount DECIMAL(10,2);

    IF p_new_plan = 'Premium' THEN
        SET v_amount = p_duration * 3000;
    ELSE
        SET v_amount = p_duration * 1500;
    END IF;

    START TRANSACTION;

    UPDATE MembershipPlans
    SET plan_type    = p_new_plan,
        duration     = p_duration,
        total_amount = v_amount,
        start_date   = CURDATE()
    WHERE member_id = p_member_id;

    IF ROW_COUNT() = 0 THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Member plan not found!';
    END IF;

    COMMIT;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE processPayment(
    IN  p_member_id INT,
    IN  p_amount    DECIMAL(10,2),
    OUT p_result    VARCHAR(100)
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Members WHERE member_id = p_member_id) THEN
        SET p_result = 'ERROR: Member not found!';
    ELSEIF p_amount <= 0 THEN
        SET p_result = 'ERROR: Invalid payment amount!';
    ELSE
        START TRANSACTION;
        INSERT INTO Payments (member_id, amount, status)
        VALUES (p_member_id, p_amount, 'Paid');
        COMMIT;
        SET p_result = 'SUCCESS: Payment recorded!';
    END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE processRefund(
    IN  p_payment_id INT,
    IN  p_member_id  INT,
    OUT p_result     VARCHAR(100)
)
BEGIN
    DECLARE v_status VARCHAR(20) DEFAULT NULL;

    SELECT status INTO v_status
    FROM Payments
    WHERE payment_id = p_payment_id
      AND member_id  = p_member_id;

    IF v_status IS NULL THEN
        SET p_result = 'ERROR: Payment not found!';
    ELSEIF v_status != 'Paid' THEN
        SET p_result = 'ERROR: Only Paid payments can be refunded!';
    ELSE
        START TRANSACTION;
        UPDATE Payments SET status = 'Refunded'
        WHERE payment_id = p_payment_id;
        SAVEPOINT refund_done;
        INSERT INTO PaymentLog (payment_id, old_status, new_status)
        VALUES (p_payment_id, 'Paid', 'Refunded');
        COMMIT;
        SET p_result = 'SUCCESS: Refund processed!';
    END IF;
END $$
DELIMITER ;

-- TRANSACTIONS

-- TRANSACTION 1: Member Payment
START TRANSACTION;
INSERT INTO Payments (member_id, amount, status)
VALUES (1, 3000.00, 'Paid');
UPDATE MembershipPlans
SET start_date = CURDATE()
WHERE member_id = 1;
COMMIT;

-- TRANSACTION 2: Refund with ROLLBACK
START TRANSACTION;
UPDATE Payments
SET status = 'Refunded'
WHERE payment_id = 1 AND member_id = 1;
-- ROLLBACK; -- uncomment to cancel
COMMIT;

-- TRANSACTION 3: Bulk Plan Update with SAVEPOINT
START TRANSACTION;
UPDATE MembershipPlans
SET plan_type = 'Premium', total_amount = 6000.00
WHERE member_id = 1;
SAVEPOINT after_member1;
UPDATE MembershipPlans
SET plan_type = 'Premium', total_amount = 6000.00
WHERE member_id = 2;
ROLLBACK TO after_member1;
COMMIT;

-- TEST CALLS

CALL addMember('suzata01','suzata123','Suzata Thapa',
               'suzata@gmail.com','9741589190','Premium',2);

CALL updateMembership(1, 'Premium', 3);

CALL processPayment(1, 3000.00, @result);
SELECT @result;

CALL processRefund(1, 1, @result);
SELECT @result;

-- VERIFY QUERIES

SELECT * FROM Users;
SELECT * FROM Members;
SELECT * FROM MembershipPlans;
SELECT * FROM Trainers;
SELECT * FROM TrainerAssignments;
SELECT * FROM Leaves;
SELECT * FROM Payments;
SELECT * FROM PaymentLog;

SELECT m.full_name, mp.plan_type, mp.total_amount
FROM Members m
JOIN MembershipPlans mp ON m.member_id = mp.member_id;

SELECT m.full_name AS member, t.full_name AS trainer
FROM TrainerAssignments ta
JOIN Members m ON ta.member_id = m.member_id
JOIN Trainers t ON ta.trainer_id = t.trainer_id;

SELECT m.full_name, l.reason, l.start_date, l.end_date, l.status
FROM Leaves l
JOIN Members m ON l.member_id = m.member_id;

SELECT m.full_name, p.amount, p.status, p.payment_date
FROM Payments p
JOIN Members m ON p.member_id = m.member_id;

DROP TABLE IF EXISTS TrainerAssignments;
DROP TABLE IF EXISTS Trainers;

CREATE TABLE IF NOT EXISTS Trainers (
    trainer_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT NOT NULL,
    full_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(100),
    phone      VARCHAR(15),
    dob        DATE,
    gender     VARCHAR(10),
    speciality VARCHAR(50),
    experience INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TrainerAssignments (
    assignment_id INT PRIMARY KEY AUTO_INCREMENT,
    trainer_id    INT NOT NULL,
    member_id     INT NOT NULL,
    assigned_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trainer_id) REFERENCES Trainers(trainer_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id)  REFERENCES Members(member_id)  ON DELETE CASCADE
);
-- STORED PROCEDURE: addTrainer

DROP PROCEDURE IF EXISTS addTrainer;


DELIMITER $$
CREATE PROCEDURE addTrainer(
    IN p_username    VARCHAR(50),
    IN p_password    VARCHAR(50),
    IN p_full_name   VARCHAR(100),
    IN p_email       VARCHAR(100),
    IN p_phone       VARCHAR(15),
    IN p_dob         DATE,
    IN p_gender      VARCHAR(10),
    IN p_speciality  VARCHAR(50),
    IN p_experience  INT
)
BEGIN
    DECLARE v_user_id INT;

    START TRANSACTION;

    -- Step 1: Insert into Users with role 'Trainer'
    INSERT INTO Users (username, password, role)
    VALUES (p_username, p_password, 'Trainer');
    SET v_user_id = LAST_INSERT_ID();

    -- Step 2: Insert into Trainers linked to that user
    INSERT INTO Trainers (user_id, full_name, email, phone, dob, gender, speciality, experience)
    VALUES (v_user_id, p_full_name, p_email, p_phone, p_dob, p_gender, p_speciality, p_experience);

    COMMIT;
END $$
DELIMITER ;

USE gymtrack;
DROP PROCEDURE IF EXISTS addMember;
DELIMITER $$

CREATE PROCEDURE addMember(
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(50),
    IN p_name VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_phone VARCHAR(15),
    IN p_gender VARCHAR(10),
    IN p_plan VARCHAR(20),
    IN p_duration INT
)
BEGIN
    INSERT INTO Members (username, password, full_name, email, phone, gender, plan, duration)
    VALUES (p_username, p_password, p_name, p_email, p_phone, p_gender, p_plan, p_duration);
END $$

DELIMITER ;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS trainerassignments;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS leaves;
DROP TABLE IF EXISTS membershipplans;
DROP TABLE IF EXISTS Members;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    role ENUM('Member','Trainer','Admin') NOT NULL
);
CREATE TABLE Members (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    gender VARCHAR(10),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);
SHOW TABLES;
CREATE TABLE MembershipPlans (
    plan_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    plan_type ENUM('Normal','Premium') NOT NULL,
    duration INT NOT NULL,
    start_date DATE,
    total_amount DECIMAL(10,2),
    FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE
);
CREATE TABLE Leaves (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    reason VARCHAR(255),
    start_date DATE,
    end_date DATE,
    status ENUM('Pending','Approved','Rejected') DEFAULT 'Pending',
    FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE
);
CREATE TABLE Attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    date_attended DATE,
    status ENUM('Present','Absent') DEFAULT 'Present',
    FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE
);
CREATE TABLE TrainerAssignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    trainer_id INT NOT NULL,
    member_id INT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trainer_id) REFERENCES Trainers(trainer_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE
);
CREATE TABLE Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Paid','Refunded','Pending') DEFAULT 'Pending',
    FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE
);

DROP PROCEDURE IF EXISTS addMember;
DELIMITER $$
CREATE PROCEDURE addMember(
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(50),
    IN p_full_name VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_phone VARCHAR(15),
    IN p_gender VARCHAR(10),
    IN p_plan_type ENUM('Normal','Premium'),
    IN p_duration INT
)
BEGIN
    DECLARE v_user_id INT;
    DECLARE v_member_id INT;
    DECLARE v_amount DECIMAL(10,2);

    IF p_plan_type = 'Premium' THEN
        SET v_amount = p_duration * 3000;
    ELSE
        SET v_amount = p_duration * 1500;
    END IF;

    START TRANSACTION;

    INSERT INTO Users(username, password, role)
    VALUES (p_username, p_password, 'Member');
    SET v_user_id = LAST_INSERT_ID();

    INSERT INTO Members(user_id, full_name, email, phone, gender)
    VALUES (v_user_id, p_full_name, p_email, p_phone, p_gender);
    SET v_member_id = LAST_INSERT_ID();

    INSERT INTO MembershipPlans(member_id, plan_type, duration, start_date, total_amount)
    VALUES (v_member_id, p_plan_type, p_duration, CURDATE(), v_amount);

    COMMIT;
END $$
DELIMITER ;

ALTER TABLE Members ADD COLUMN address VARCHAR(255);
SHOW DATABASES;
DESCRIBE Members;
USE gymtrack;
ALTER TABLE Members ADD COLUMN dob DATE;
ALTER TABLE Members ADD COLUMN goal VARCHAR(255);