package com.msgrserver.action;

public enum ActionType {
    SEND_TEXT,
    SEND_FILE,
    SIGN_UP,
    SIGN_IN,
    UPDATE_PROFILE_PHOTO,
    CREATE_GROUP,
    GET_USER_CHATS,
    JOIN_CHAT_WITH_LINK,
    ADD_USER_BY_ADMIN,
    DELETE_USER_BY_ADMIN,
    LEAVE_PUBLIC_CHAT,
    SELECT_NEW_ADMIN_PUBLIC_CHAT,
    DELETE_ADMIN_PUBLIC_CHAT,
    VIEW_USER_PROFILE,
    DELETE_PUBLIC_CHAT,
    EDIT_PROFILE,
    EDIT_PROFILE_PUBLIC_CHAT
}
