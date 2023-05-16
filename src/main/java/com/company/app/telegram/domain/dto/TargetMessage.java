package com.company.app.telegram.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TargetMessage {

	String chatName;
	String message;
}
