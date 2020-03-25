package si.lista1.model;

import java.util.Map;

public class DataFile {
    private String name;
    private String type;
    private String comment;
    private String dimension;
    private String edgeWeightType;
    private String displayDataType;
    private Map<Integer, Place> places;

    public DataFile(String name, String type, String comment, String dimension, String edgeWeightType, String displayDataType, Map<Integer, Place> places) {
        this.name = name;
        this.type = type;
        this.comment = comment;
        this.dimension = dimension;
        this.edgeWeightType = edgeWeightType;
        this.displayDataType = displayDataType;
        this.places = places;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getEdgeWeightType() {
        return edgeWeightType;
    }

    public void setEdgeWeightType(String edgeWeightType) {
        this.edgeWeightType = edgeWeightType;
    }

    public String getDisplayDataType() {
        return displayDataType;
    }

    public void setDisplayDataType(String displayDataType) {
        this.displayDataType = displayDataType;
    }

    public Map<Integer, Place> getPlaces() {
        return places;
    }

    public void setPlaces(Map<Integer, Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "name = " + name + "\ntype = " + type + "\ncomment = " + comment + "\n dimension = " + dimension
                + "\n edgeWeightType = " + edgeWeightType + "\ndisplayDataType = " + displayDataType
                + "\nplaces = \n" + places;
    }
}
