//http://web.chacuo.net/netrsakeypair 生成密钥对
import {JSEncrypt} from "jsencrypt";

// 公钥
const pubKey = "";

// 私钥
const priKey = "";


export function encrypt(txt) {
    const encryptor = new JSEncrypt();
    encryptor.setPublicKey(pubKey);
    return encryptor.encrypt(txt);
}


export function decrypt(txt) {
    const encryptor = new JSEncrypt();
    encryptor.setPrivateKey(priKey);
    return encryptor.decrypt(txt);
}
