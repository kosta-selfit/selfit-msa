package com.oopsw.accountservice.jpa;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "member_id", unique = true, nullable = false)
	private String memberId;

	@Column(name = "email", unique = true, nullable = false, length = 50)
	private String email;

	@Column(name = "pw", nullable = false)
	private String pw;

	@Column(name = "name", length = 20)
	private String name;

	@Column(name = "nickname", unique = true, length = 20)
	private String nickname;

	@Column(name = "gender", length = 2)
	private String gender;

	@Column(name = "birthday", length = 30)
	private Date birthday;

	@Column(name = "height", length = 5)
	private Float height;

	@Column(name = "weight", length = 5)
	private Float weight;

	@Column(name = "goal", length = 5)
	private String goal;

	@Column(name = "join_date", length = 30)
	private Date joinDate;

	@Column(name = "member_type", length = 30)
	private String memberType;

	@Column(name = "profile_img")
	private String profileImg;

}
