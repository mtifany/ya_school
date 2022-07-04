package ru.ya_school.models;

import org.springframework.lang.Nullable;
import ru.ya_school.dto.EntityDto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RequestObject implements Serializable {
    @Nullable
    private List<EntityDto> items;
    @NotNull
    private String updateDate;
    @Nullable
    public List<EntityDto> getItems() {
        return items;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestObject that = (RequestObject) o;
        return Objects.equals(items, that.items) && Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, updateDate);
    }

    @Override
    public String toString() {
        return "RequestObject{" +
                "items=" + items +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
