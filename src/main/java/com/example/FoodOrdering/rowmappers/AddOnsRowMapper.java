package com.example.FoodOrdering.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.FoodOrdering.data.AddOnQsns;

public class AddOnsRowMapper implements RowMapper<AddOnQsns>{

	@Override
	public AddOnQsns mapRow(ResultSet rs, int rowNum) throws SQLException {

		return null;
	}

}
