package com.sprint.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;
import java.util.Optional;

/**
 * The Interface SprintDAO.
 */
public interface SprintDAO {

    /**
     * Gets the  template.
     *
     * @return the  template
     */
    JdbcTemplate getJdbcTemplate();

    /**
     * Gets the list of tables.
     *
     * @return the list of tables
     */
    default List<String> getListOfTables() {
        return getJdbcTemplate().query("show tables", new SingleColumnRowMapper<>(String.class));
    }

    /**
     * Gets the row count.
     *
     * @param tableName the table name
     * @return the row count
     */
    default Optional<Integer> getRowCount(final String tableName) {
        return Optional.ofNullable(getJdbcTemplate().queryForObject("select count(*) from " + tableName, Integer.class));
    }

    /**
     * Gets the sprint list.
     *
     * @param tableName the table name
     * @return the sprint list
     */
    default List<String> getSprintList(final String tableName) {
        return getJdbcTemplate().query("select sprint from " + tableName, new SingleColumnRowMapper<>(String.class));
    }
}
