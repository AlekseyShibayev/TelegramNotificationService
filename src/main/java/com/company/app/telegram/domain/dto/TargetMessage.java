package com.company.app.telegram.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TargetMessage {

	Long chatId;
	String message;
}
