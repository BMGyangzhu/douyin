export const required = (fieldName = '该字段') => {
    return v => !!v || `${fieldName}不能为空`
}

export const minLength = (length, fieldName = '字段') => {
    return v => (v && v.length >= length) || `${fieldName}不能少于${length}位`
}

export const matchPassword = (getPassword, fieldName = '密码') => {
    return v => v == getPassword() || `两次输入的${fieldName}不一致`
}

// return v ... 的语法是高阶函数
// 是否需要动态访问其他变量的值（比如密码），从而决定是否需要使用箭头函数。

