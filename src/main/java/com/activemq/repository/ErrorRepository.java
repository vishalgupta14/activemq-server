package com.activemq.repository;

import com.activemq.entities.Error;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ErrorRepository extends JpaRepository<Error, Long> {

    Page<Error> findByInsertedOnAfter(Date date, Pageable pageable);

    void deleteByInsertedOnAfter(Date date);
}
