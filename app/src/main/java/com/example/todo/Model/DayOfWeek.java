package com.example.todo.Model;

import android.content.Context;

import com.example.todo.R;

public enum DayOfWeek {
    MON {
        // overriding toString() for SMALL
        public String toString() {
            return "Thứ hai";
        }}
    ,TUE {

        // overriding toString() for SMALL
        public String toString() {
            return "Thứ ba";
        }
    }
    ,WED{

        // overriding toString() for SMALL
        public String toString() {
            return "Thứ tư";
        }
    }
    ,THU{

        // overriding toString() for SMALL
        public String toString() {
            return "Thứ năm";
        }
    }
    ,FRI{

        // overriding toString() for SMALL
        public String toString() {
            return "Thứ sáu";
        }
    }
    ,SAT{

        // overriding toString() for SMALL
        public String toString() {
            return "Thứ bảy";
        }
    }
    ,SUN{

        // overriding toString() for SMALL
        public String toString() {
            return "Chủ nhật";
        }
    }
}
