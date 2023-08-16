package com.twinkle.JakSim.model.dto.Enum;

public enum TimetableType {
    CONSULTING("1"),
    PERSONAL("2"),
    GROUP("3");

    private String type;

    TimetableType(String type) {
        this.type = type;
    }

//    public static TimetableType findByType(String types) {
//        for(TimetableType type : TimetableType.values()) {
//            if(type.equals(types)) {
//                return type;
//            }
//        }
//        throw new RuntimeException();
//    }

}
