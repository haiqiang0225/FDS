import request from "@/utils/request";

const urlPrefix = "/api/auth";

export function queryRoleList(param) {
    return request.get(
        urlPrefix + "/sys/role/list?" + param
    )
}

export function deleteRole(param) {
    return request.post(
        urlPrefix + "/sys/role/delete",
        param
    )
}

export function getMenusByRoleName(roleName) {
    return request.get(
        urlPrefix + "/sys/role/menus/0/" + roleName
    )
}