import useClipboard from "vue-clipboard3";
import {ElNotification} from "element-plus";

const {toClipboard} = useClipboard()

export async function copyToClipBoard(id) {
    try {
        await toClipboard(id);
        ElNotification({
            title: '复制成功',
            message: '已复制到剪切板',
            type: 'success',
        });
    } catch (e) {
        ElNotification({
            title: '复制失败',
            message: '您的浏览器不支持复制',
            type: 'error',
        })
        console.log(e);
    }
}