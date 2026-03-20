package TodayFX;

public class ResultServiceImpl implements ResultService {
    @Override
    public void calculate(Student s) {
        s.total = s.pop + s.cn + s.db;
        s.average = s.total / 3;
        if (s.pop >= 40 && s.cn >= 40 && s.db >= 40) {
            s.result = "PASS";
        } else {
            s.result = "FAIL";
        }
    }
}

