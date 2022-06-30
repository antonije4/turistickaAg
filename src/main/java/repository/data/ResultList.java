package repository.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class ResultList<T> {
	private List<T> list;
	private long totalResultCount = -1;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(long totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public ResultList() {
		list = new ArrayList<>();
	
	}
	
	
	public static <T> ResultList<T> create(List<T> results, long totalResulCount){
		ResultList<T> result = new ResultList<>();
		
		if(results != null) {
			result.getList().addAll(results);
		}
		result.setTotalResultCount(totalResulCount);
		
		return result;
	}
	
	public void clear() {
		if (list != null) {
			this.list.clear();
		}
		
		this.setTotalResultCount(-1);
	}
}
