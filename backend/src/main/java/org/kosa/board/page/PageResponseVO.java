package org.kosa.board.page;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class PageResponseVO <T> implements Iterable<T> {
	@JsonIgnore
	private final Page<T> page;
	private final int startPage;
	private final int endPage;
	@JsonIgnore
	private final int groupSize;

	public PageResponseVO(Page<T> page, int groupSize) {
		this.page = page;
		this.groupSize = groupSize;
		startPage = (page.getNumber() / groupSize) * groupSize;
		endPage = Math.min(startPage + groupSize - 1, page.getTotalPages() - 1);
	}

	public boolean isFirstGroup() {
		return startPage == 0;
	}

	public boolean isLastGroup() {
		return page.getTotalPages() - 1 == endPage;
	}

	public int getNumber() {
		return page.getNumber();
	}

	public int getSize() {
		return page.getSize();
	}

	public boolean isEmpty() {
		return page.isEmpty();
	}

	public List<T> getContent() {
		return page.getContent();
	}

	@Override
	public Iterator<T> iterator() {
		return page.iterator();
	}
}
