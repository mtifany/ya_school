package ru.ya_school.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public abstract class Entity {

    @Id
    @Column(name = "id")
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name ="name")
    @NotNull
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityType type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "date")
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Category parent;

    @Column(name = "parent_name")
    @Type(type="pg-uuid")
    private UUID parentId;

   @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<Entity> children = new ArrayList<>();

    @JsonIgnore
    @Column(name ="children_number")
    private Integer children_number = 0;

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", date=" + date +
                ", parent=" + parent +
                ", parentId=" + parentId +
                ", children_number=" + children_number +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public Integer getPrice() {
        if (type == EntityType.OFFER) return price;

        if (children == null) children = new ArrayList<>();

        return  GetCategoryPrice();

    }
    private Integer GetCategoryPrice(){
        double sum =0;
        int counter = 0;

        if(children.size() == 0) return 0;

        for(Entity child: children){
            if(child.type == EntityType.CATEGORY){
                sum += child.GetCategoryPrice()*child.getChildren_number();
                counter+=child.getChildren_number();
            }
            else
            {
                sum+=child.price;
                counter++;
            }
        }
            if (counter == 0) return null;

            return (int) sum/counter;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public int getChildren_number() {
        if (children == null) return 0;

        return children.size();
    }

    public void setChildren_number(int children_number) {
        this.children_number = children_number;
    }

    public List<Entity> getChildren() {

        if (children.size() == 0) return null;

        return children;
    }

    public void setChildren(List<Entity> children) {
        this.children = children;
    }

    public void update(Entity updated) {
        this.name = updated.getName();
       this.price = updated.getPrice();
        this.date = updated.getDate();
       this.parentId = updated.getParentId();
        this.parent = updated.getParent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity that = (Entity) o;
        return id.equals(that.id);
    }


}

