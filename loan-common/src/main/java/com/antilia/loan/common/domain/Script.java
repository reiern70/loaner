package com.antilia.loan.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Stores an "executed" script.
 * 
 * @author reiern70
 */

@Entity
@Table(name="i_scripts")
public class Script extends EntityBase {

	@Column(unique=true)
	private String scriptId;
	
	private Date executed;
	
	public Script() {
	}

	public Date getExecuted() {
		return executed;
	}

	public void setExecuted(Date executed) {
		this.executed = executed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((scriptId == null) ? 0 : scriptId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Script other = (Script) obj;
		if (scriptId == null) {
			if (other.scriptId != null)
				return false;
		} else if (!scriptId.equals(other.scriptId))
			return false;
		return true;
	}

	public String getScriptId() {
		return scriptId;
	}

	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}

}
