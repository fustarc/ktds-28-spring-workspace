package com.ktdsuniversity.edu.replies.vo.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RepliesListVO {

	private long repliesCount;
	
	private List<RepliesVO> replieList;
	
}
