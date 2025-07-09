package com.oopsw.checklistservice.jpa;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="checklist")
public class ChecklistEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false, insertable = false)
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private Date checklistDate;
	@Column(nullable = false)
	private Integer isChecked;
	@Column(nullable = false)
	private String checklistContent;
	@Column(nullable = false)
	private String memberId;
	@Column(nullable = false, unique = true)
	private String checklistId;
}
