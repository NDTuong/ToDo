package com.example.todo.Model;


import com.example.todo.R;

public enum DayOfWeek {
    MON {
        // overriding toString() for SMALL
        public String toString() {
            return String.valueOf(R.string.monday);
        }}
    ,TUE {

        // overriding toString() for SMALL
        public String toString() {
            return String.valueOf(R.string.tuesday);
        }
    }
    ,WED{

        // overriding toString() for SMALL
        public String toString() {
            return String.valueOf(R.string.wednesday);
        }
    }
    ,THU{

        // overriding toString() for SMALL
        public String toString() {
            return String.valueOf(R.string.thursday);
        }
    }
    ,FRI{

        // overriding toString() for SMALL
        public String toString() {
            return String.valueOf(R.string.friday);
        }
    }
    ,SAT{

        // overriding toString() for SMALL
        public String toString() {
            return String.valueOf(R.string.saturday);
        }
    }
    ,SUN{

        // overriding toString() for SMALL
        public String toString() {
            return String.valueOf(R.string.sunday);
        }
    }
}
