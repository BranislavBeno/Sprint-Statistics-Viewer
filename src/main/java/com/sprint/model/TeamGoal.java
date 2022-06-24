package com.sprint.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * The Class TeamGoal.
 *
 * @param teamName The team name.
 * @param goals    The goals.
 */
public record TeamGoal(String teamName, String[] goals) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamGoal teamGoal = (TeamGoal) o;

        if (!Objects.equals(teamName, teamGoal.teamName)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(goals, teamGoal.goals);
    }

    @Override
    public int hashCode() {
        int result = teamName != null ? teamName.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(goals);
        return result;
    }

    @Override
    public String toString() {
        return "TeamGoal{" +
                "teamName='" + teamName + '\'' +
                ", goals=" + Arrays.toString(goals) +
                '}';
    }
}
