import http from "@/utils/request";

const urlPrefix = "/api/statistics";

export function getFaultCountByDeviceType() {
    return http.get(
        urlPrefix + "/getFaultCountByDeviceType"
    )
}


export function getFaultCountByFaultType() {
    return http.get(
        urlPrefix + "/getFaultCountByFaultType"
    )
}

export function getFaultCountByFaultDegree() {
    return http.get(
        urlPrefix + "/getFaultCountByFaultDegree"
    )
}

export function getRunningStatesCount() {
    return http.get(
        urlPrefix + "/runningStatesCount"
    )
}

export function getRecentlyFaults() {
    return http.get(
        urlPrefix + "/recentFaults"
    )
}