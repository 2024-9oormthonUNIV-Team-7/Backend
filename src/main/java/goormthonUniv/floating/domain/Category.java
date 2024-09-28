package goormthonUniv.floating.domain;

public enum Category {
    first_meeting, time_tuning, small_talk;

    public static Category fromString(String categoryStr) {
        for (Category c : Category.values()){
            if (c.name().equals(categoryStr)){
                return c;
            }
        }
        return null;
    }
}
