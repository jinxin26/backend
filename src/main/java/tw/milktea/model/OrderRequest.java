package tw.milktea.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @Nullable
    private String orderId;

    @Nullable
    private List<String> orderStatus;

    @Nullable
    Boolean deleted;

    @Nullable
    Integer pageNumber;

    @Nullable
    Integer pageSize;

    @Nullable
    String sortRule;

    @Nullable
    String sortField;

    public Integer getPageNumber() { return pageNumber == null ? 1 : pageNumber; }

    public Integer getPageSize() { return pageSize == null ? 10 : pageSize; }

    public String getSortRule() { return sortRule == null ? "descend" : sortRule; }

    public boolean isDeleted() { return deleted == null ? false : deleted; }
}
