import request from "@/utils/request";
import {appendUrls, getRequestBaseUrl} from "@/utils/request";

const urlPrefix = "/api/auth";

/************************************************* login&logout *************************************************/
export function login(data) {
    return request.post(
        urlPrefix + "/login",
        data
    );
}

export function logout() {
    return request.get(
        urlPrefix + "/logout"
    )
}

export function getAvatar(uri) {
    return appendUrls(getRequestBaseUrl(), "api", "auth", uri);
}

/************************************************* user options *************************************************/

export function queryUserList(param) {
    return request.get(
        urlPrefix + "/sys/user/list?" + param
    )
}

export function deleteUser(param) {
    return request.post(
        urlPrefix + "/sys/user/delete",
        param
    )
}

export function updateUser(user) {
    return request.post(
        urlPrefix + "/sys/user/save",
        user
    )
}

export function queryRolesByUser(param) {
    return request.get(
        urlPrefix + "/sys/user/roles?" + param
    )
}

export function updateUserRoles(param, data) {
    const headers = {'Content-Type': 'application/json;charset=utf-8'}
    return request.post(
        urlPrefix + "/api/sys/user/updateRoles?" + param,
        data,
        {headers}
    )
}

export function resetPass(user) {

}

export function updateStatus(user) {

}