import request from "@/utils/request";

const urlPrefix = "/api/auth";

export function getMenuTreeList(param) {
    let url = urlPrefix + "/sys/menu/treeList"
    url = param ? url + "?" + param : url;
    return request.get(
        url
    )
}
