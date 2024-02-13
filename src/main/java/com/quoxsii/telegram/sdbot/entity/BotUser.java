package com.quoxsii.telegram.sdbot.entity;

import com.quoxsii.telegram.sdbot.locale.SdBotLocale;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bot_user")
public class BotUser {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private SdBotLocale language;

    @Column(name = "seed", nullable = false)
    private Long seed;

    @Column(name = "use_seed", nullable = false)
    private Boolean useSeed;

    @Column(name = "dialog_id")
    private Integer dialogId;

    @Column(name = "dialog_state")
    @Enumerated(EnumType.STRING)
    private SdBotUiComponent dialogState;

    @Column(name = "prompt")
    private String prompt;

    @Column(name = "negative_prompt")
    private String negativePrompt;

    public BotUser(Long id) {
        this.id = id;
        this.language = SdBotLocale.ENGLISH;
        this.seed = -1L;
        this.useSeed = false;
    }

    public boolean hasDialogId() {
        return dialogId != null;
    }

    public boolean hasDialogState() {
        return dialogState != null;
    }

}
