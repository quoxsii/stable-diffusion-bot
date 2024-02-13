package com.quoxsii.telegram.sdbot.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Txt2ImgDto {

    private String prompt;

    @Builder.Default
    @JsonProperty("negative_prompt")
    private String negativePrompt = "";

    @Builder.Default
    private Integer width = 768;

    @Builder.Default
    private Integer height = 512;

    @Builder.Default
    @JsonProperty("cfg_scale")
    private Integer cfgScale = 7;

    @Builder.Default
    @JsonProperty("s_min_uncond")
    private Integer sMinUncond = 0;

    @Builder.Default
    @JsonProperty("override_settings_restore_afterwards")
    private Boolean overrideSettingsRestoreAfterwards = true;

    @JsonProperty("s_tmax")
    private Integer sTmax;

    @Builder.Default
    @JsonProperty("s_tmin")
    private Integer sTmin = 0;

    @Builder.Default
    @JsonProperty("s_noise")
    private Integer sNoise = 1;

    @Builder.Default
    private Boolean tiling = false;

    /*@JsonProperty("hr_sampler_name")
    private String hrSamplerName;*/

    @Builder.Default
    @JsonProperty("s_churn")
    private Integer sChurn = 0;

    /*private Integer eta;*/

    @JsonProperty("override_settings")
    private OverrideSettings overrideSettings;

    @Builder.Default
    @JsonProperty("sampler_name")
    private String samplerName = "DPM++ 2M SDE Karras";

    @Builder.Default
    @JsonProperty("hr_resize_y")
    private Integer hrResizeY = 0;

    @Builder.Default
    @JsonProperty("hr_resize_x")
    private Integer hrResizeX = 0;

    @Builder.Default
    @JsonProperty("alwayson_scripts")
    private AlwaysonScripts alwaysonScripts = new AlwaysonScripts();

    @Builder.Default
    private Integer subseed = -1;

    @Builder.Default
    @JsonProperty("enable_hr")
    private Boolean enableHr = false;

    @Builder.Default
    @JsonProperty("hr_prompt")
    private String hrPrompt = "";

    @Builder.Default
    @JsonProperty("hr_negative_prompt")
    private String hrNegativePrompt = "";

    @Builder.Default
    @JsonProperty("do_not_save_samples")
    private Boolean doNotSaveSamples = true;

    @Builder.Default
    @JsonProperty("do_not_save_grid")
    private Boolean doNotSaveGrid = true;

    @Builder.Default
    @JsonProperty("subseed_strength")
    private Integer subseedStrength = 0;

    /*@Builder.Default
    @JsonProperty("firstphase_width")
    private Integer firstphaseWidth = 0;*/

    /*@Builder.Default
    @JsonProperty("save_images")
    private Boolean saveImages = false;*/

    /*@Builder.Default
    @JsonProperty("send_images")
    private Boolean sendImages = true;*/

    @Builder.Default
    @JsonProperty("hr_second_pass_steps")
    private Integer hrSecondPassSteps = 0;

    /*@JsonProperty("denoising_strength")
    private Integer denoisingStrength;*/

    @Builder.Default
    @JsonProperty("hr_upscaler")
    private String hrUpscaler = "Latent";

    /*@JsonProperty("samples_index")
    private String samplerIndex;*/

    /*@JsonProperty("hr_checkpoint_name")
    private String hrCheckpointName;*/

    @Builder.Default
    @JsonProperty("batch_size")
    private Integer batchSize = 1;

    private Comments comments;

    @Builder.Default
    @JsonProperty("restore_faces")
    private Boolean restoreFaces = false;

    @Builder.Default
    @JsonProperty("n_iter")
    private Integer nIter = 1;

    @Builder.Default
    @JsonProperty("script_args")
    private List<Object> scriptArgs = new ArrayList<>();

    @JsonProperty("script_name")
    private String scriptName;

    @Builder.Default
    private Long seed = -1L;

    @Builder.Default
    @JsonProperty("seed_resize_from_h")
    private Integer seedResizeFromH = -1;

    @Builder.Default
    @JsonProperty("seed_resize_from_w")
    private Integer seedResizeFromW = -1;

    @Builder.Default
    private Integer steps = 20;

    private List<String> styles;

    /*@JsonProperty("firstphase_height")
    private Integer firstphaseHeight;*/

    @Builder.Default
    @JsonProperty("hr_scale")
    private Integer hrScale = 2;

    @Builder.Default
    @JsonProperty("disable_extra_networks")
    private Boolean disableExtraNetworks = false;

    /*@JsonProperty("refiner_checkpoint")
    private String refinerCheckpoint;*/

    /*@JsonProperty("refiner_switch_at")
    private Integer refinerSwitchAt;*/

}