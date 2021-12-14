package br.com.valerianosoft.client.common.filter;

import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PageFilter {

  private static final int DEFAULT_PAGE_INDEX = 1;
  private static final int DEFAULT_ITEMS_PER_PAGE = 50;

  @Min(value = 1, message = "Items per page must be greater than zero.")
  private Integer itemsPerPage;

  @Min(value = 1)
  private Integer pageIndex;

  public PageFilter() {
    this.itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
    this.pageIndex = DEFAULT_PAGE_INDEX;
  }

  public Pageable toPageable() {
    return PageRequest.of(pageIndex - 1, itemsPerPage);
  }
}
