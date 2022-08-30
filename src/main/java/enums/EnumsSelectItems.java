package enums;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
@Named("enums")
@SessionScoped
@Slf4j
public class EnumsSelectItems implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private List<SelectItem> ugostiteljTypes;
	@Getter
	private List<SelectItem> maxResults;

	
	@PostConstruct
	public void init() {
		createItems();
	}
	
	public void createItems() {
		ugostiteljTypes = createSelectItemsForEnum(UgostiteljType.class, true);
		createSelectItemsForMaxResult();
	}


	private void createSelectItemsForMaxResult() {
		maxResults = new ArrayList<>();

		maxResults.add(new SelectItem(10, "10"));
		maxResults.add(new SelectItem(50, "50"));
		maxResults.add(new SelectItem(100, "100"));
		maxResults.add(new SelectItem(500, "500"));
		maxResults.add(new SelectItem(1000, "1 000"));
		maxResults.add(new SelectItem(10000, "10 000"));
		maxResults.add(new SelectItem(-1, "All"));
	}

	public <E extends Enum<?>> List<SelectItem> createSelectItemsForEnum(Class<E> clazz, boolean sort) {
		List<SelectItem> items = new ArrayList<>();

		for (E e : clazz.getEnumConstants()) {

			E value = e;

			items.add(new SelectItem(value, value.name()));
		}

		if(sort) {
			items.sort(Comparator.comparing(SelectItem::getLabel));
		}

		return items;
	}
	
}
