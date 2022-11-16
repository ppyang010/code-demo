package com.code.data;



import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 *
 * @author caicy
 * @date 2017/10/5
 * hibernate 实体类
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_users")
public class UserTestEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    //主键和自增
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private Integer locked;

    public UserTestEntity() {
    }


}
