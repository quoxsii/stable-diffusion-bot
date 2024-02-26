package com.quoxsii.telegram.sdbot.repository;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
}
