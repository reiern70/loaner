package com.antilia.loan.common.domain;

import javax.persistence.*;

@MappedSuperclass
public class EntityBase implements IIdentifiable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;
	
	EntityBase() {
	}

    public EntityBase(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
