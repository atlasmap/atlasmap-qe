package io.atlasmap.qe.test.utils;

public enum HoverAction {

    SHOW_MAPPING_DETAILS("show-mapping-details-button"),
    DISCONNECT_FROM_SELECTED_MAPPING("disconnect-from-the-selected-mapping-button"),
    CONNECT_TO_SELECTED_MAPPING("connect-to-the-selected-mapping-button"),
    CREATE_NEW_MAPPING("create-new-mapping-button"),
    REMOVE_PROPERTY("remove-property-button"),
    REMOVE_CONSTANT("remove-constant-button"),
    EDIT_PROPERTY("edit-property-button"),
    EDIT_CONSTANT("edit-constant-button");

    public final String label;

    HoverAction(String label) {
        this.label = label;
    }
}
