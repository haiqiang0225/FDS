import http from "@/utils/request";


const urlPrefix = "/api/monitor";

export async function getSysLoad() {
    return await http.get(urlPrefix + "/sysLoad", {
        doNotShowLoading: true,
    })
}


