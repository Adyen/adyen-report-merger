package com.adyen.reportMerger.entities;

/**
 * Created by andrew on 9/22/16.
 */
public enum MergeTypes {
    BATCHRANGE ("Batch range", "batch"),
    DATERANGE ("Date range", "date");

    String mergeTypeDescription;
    String mergeType;

    MergeTypes(String mergeTypeDescription, String mergeType) {

        this.mergeTypeDescription = mergeTypeDescription;
        this.mergeType = mergeType;
    }

    public String getMergeTypeDescription() {
        return mergeTypeDescription;
    }

    public void setMergeTypeDescription(String mergeTypeDescription) {
        this.mergeTypeDescription = mergeTypeDescription;
    }

    public String getMergeType() {
        return mergeType;
    }

    public void setMergeType(String mergeType) {
        this.mergeType = mergeType;
    }

    public static MergeTypes getMergeTypeFromDescription(String text) {
        if (text != null) {
            for (MergeTypes mt : MergeTypes.values()) {
                if (text.equalsIgnoreCase(mt.getMergeTypeDescription())) {
                    return mt;
                }
            }
        }
        return null;
    }
}
