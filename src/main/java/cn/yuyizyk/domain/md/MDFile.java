package cn.yuyizyk.domain.md;

import java.nio.file.Path;

import lombok.Data;

@Data
public class MDFile {
	/** 物理路径 */
	private String path;
	private String filename;
	private String body;

	public static final MDFile byPath(Path p) {
		return new MDFile();
	}

}
