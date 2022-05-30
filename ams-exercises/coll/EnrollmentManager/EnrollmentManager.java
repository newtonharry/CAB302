package coll.EnrollmentManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Static class for managing student enrollments.
 */
public class EnrollmentManager {

    /**
     * Map containing <unit, students> pairs.
     */
    private static Map<String, Set<String>> enrollments = new HashMap<String, Set<String>>();

    /**
     * Enrolls a student into a unit.
     *
     * @param unit - the unit for the student to be enrolled
     * @param student - the student
     */
    public static void enroll(String unit, String student) {
        if(enrollments.containsKey(unit)){
            enrollments.get(unit).add(student);
        }else{
            Set<String>  studentSet = new HashSet<String>();
            studentSet.add(student);

            enrollments.put(unit,studentSet);
        }

    }

    /**
     * Gets the HashMap containing the current enrollments.
     *
     * @return the enrollments
     */
    public static Map<String, Set<String>> getEnrollments() {
        return enrollments;
    }

    /**
     * Removes all enrollments form the HashMap.
     */
    public static void wipeEnrollments() {
        enrollments.clear();
    }

    /**
     * Withdraws a student from a unit.
     *
     * @param unit - the unit for the student to be withdrawn from
     * @param student - the student
     */
    public static void withdrawEnrollment(String unit, String student) {
        enrollments.get(unit).remove(student);
    }

    /**
     * Withdraws a student from all units they are enrolled in.
     *
     * @param student - the student
     */
    public static void withdrawStudent(String student) {
        enrollments.forEach((unit,students) -> students.remove(student));
    }

    /**
     * Gets a set of all students of a particular discipline. E.g. If discipline is
     * "ABC" then return a collection of all students enrolled in units that start
     * with "ABC", so ABC301, ABC299, ABC741 etc.
     *
     * @param discipline - the discpline
     * @return the set of students
     */
    public static Set<String> getStudents(String discipline) {
        Set<String>  studentSet = new HashSet<String>();
        enrollments.forEach((unit,students) -> {
             if(unit.startsWith(discipline)){
                 studentSet.addAll(students);;
             }
        });

        return studentSet;
    }
}