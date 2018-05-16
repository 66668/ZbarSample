package com.sfs.zbar.net;

public class Urls {

    /**
     * 服务器地址需要和后台确认，这里只是写个样例
     */

    // public static final String URL_HOST = "http://42.96.201.183/shoufashi/adminApi/";//原正式
//      public static final String URL_HOST = "http://adminapi.shoufashi.com/";//正式
//	public static final String URL_HOST = "http://120.92.21.109/adminApi/";
//	public static final String URL_HOST = "http://192.168.20.201/adminApi/";//内网测试
	public static final String URL_HOST = "http://mydev.shoufashi.com/adminApi/";//外网测试

    public static class CmdGet {

        // 后台
        /**
         * 上传图片
         */
        public static String IMAGEFILE = "ImageFile/index.php";

        /**
         * 更新图片
         */
        public static String UPDATEPIC = "UpdatePic/";

        /**
         * 物业代收
         */
        public static String DISPATCH = "Dis/";
        /**
         * 获取快递列表
         */
        public static String EXPRESS = "Express/";
        /**
         * 获取省份列表
         */
        public static String PROVINCE = "Province/";

        /**
         * 获取某社區用戶手機號列表
         */
        public static String SelVphone = "SelVphone/";

        /**
         * 获取货架类型
         */
        public static String CONTAINSORT = "ContaiSort/";

        /**
         * 登录接口
         */
        public static String LOGIN = "Login/";

        /**
         * 物业代发
         */
        public static String TAKING = "Taking/";

        /**
         * 用户取件
         */
        public static String INVITE = "Invite/";

        /**
         * 获取货架
         */
        public static String CONTAI = "Contai/";

        /**
         * 获取主页数字
         */
        public static String AD = "Advertise/";
        /**
         * 获取主页数字--充值余额
         */
        public static String Balance = "Balance/";

        /**
         * 获取主页数字
         */
        public static String EDITIONNUM = "EditionNumber/";

        /**
         * 货架管理
         */
        public static String CONTAINER = "Container/";

        /**
         * 新填货架
         */
        public static String ADDCONTAINER = "AddContainer/";

        /**
         * 用户管理
         */
        public static String USER = "User/";

        /**
         * 删除用户
         */
        public static String DELUSER = "DelUser/";

        /**
         * 新填用户
         */
        public static String ADDUSER = "AddUser/";

        /**
         * 编辑用户
         */
        public static String EDITUSER = "EditUser/";
        /**
         * 确认取件
         */
        public static String AFFIRMINVITE = "AffirmInvite/";

        /**
         * 上门送件
         */
        public static String PUSHORDER = "PushOrder/";

        /**
         * 快速派送
         */
        public static String PUSHEXP = "PushExp/";
        /**
         * 快速发件
         */
        public static String QSTAKING = "QsTaking/";

        /**
         * 代收查询
         */
        public static String QUERYDISPATCH = "QueryDis/";

        /**
         * 根据单号获取快递公司
         */
        public static String QUERYEXPRESS = "QueryExpress/";

        /**
         * 快递状态
         */
        public static String GETSTATUS = "GetStatus/";

        /**
         * 获取货架
         */
        public static String EDITCON = "EditContainer/";

        /**
         * 代收编辑
         */
        public static String EDITDIS = "EditDispatch/";

        /**
         * 代发编辑
         */
        public static String EDITTAK = "EditTaking/";

        /**
         * 发件查询
         */
        public static String QUERYTAKING = "QueryTaking/";

        /**
         * 代收查询
         */
        public static String DSQUERY = "DsQuery/";

        /**
         * 代收查询
         */
        public static String DFQUERY = "DfQuery/";

        /**
         * 注册接口
         */
        public static String REGISTER = "Register/";

        /**
         * 获取验证码
         */
        public static String VERIFY = "Verify/";

        /**
         * 验证手机号
         */
        public static String CHECKMOBILE = "CheckMobile/";

        /**
         * 绑定手机号
         */
        public static String BINDING = "Binding/";

        /**
         * 查询接口
         */
        public static String QUERY = "Query/";

        /**
         * 查询历史
         */
        public static String QUERYLIST = "QueryList/";

        /**
         * 社区直供列表
         */
        public static String SUPPLY = "supply/";

        /**
         * 报修预约
         */
        public static String REPORDER = "RepOrder/";

        /**
         * 获取报修项目
         */
        public static String REPSORT = "RepSort/";

        /**
         * 获取保洁项目
         */
        public static String CLEANSORT = "CleanSort/";

        /**
         * 保洁预约接口
         */
        public static String CLEANORDER = "CleanOrder/";

        /**
         * 获取社区信息接口
         */
        public static String DETAIL = "detail/";
        /**
         * 设置新密码
         */
        public static String PASSWDMODIFY = "PasswdModify/";

        /**
         * 获取社区列表
         */
        public static String ELICITLIST = "elicit/";
        /**
         * 保洁删除
         */
        public static String CLEAN_DEL = "CleanDel/";

        /**
         * 保洁删除
         */
        public static String DELETECLEAN = "DeleteClean/";

        /**
         * 报修列表
         */
        public static String REPINFO = "RepInfo/";

        /**
         * 预约接口
         */
        public static String ORDER = "Order/";

        /**
         * 报修返修
         */
        public static String REPAIRS = "Repairs/";

        /**
         * 保洁列表
         */
        public static String CLEANINFO = "CleanInfo/";

        /**
         * 催促
         */
        public static String CUICU = "Urge/";

        /**
         * 报修删除
         */
        public static String REPDEL = "RepDel/";

        /**
         * 保洁催促
         */
        public static String CLEAN_CUICU = "CleanCuicu/";

        /**
         * 服务人员信息
         */
        public static String CLEAN_MEMBER = "CleanMember/";

        /**
         * 服务人员信息
         */
        public static String REPMEMBER = "RepMember/";

        /**
         * 直供商品详情
         */
        public static String ZHIGONG_DETAIL = "SupDetail/";

        /**
         * 订单商品详情
         */
        public static String ORDDETAIL = "OrdDetail/";

        /**
         * 获取送货地址
         */
        public static String ADDRESS = "DeliveryAdd/";

        /**
         * 获取订单列表
         */
        public static String DINGDANLIST = "Orders/";

        /**
         * 生成订单
         */
        public static String ORDGENERATED = "OrdGenerated/";

        /**
         * 取消或删除订单
         */
        public static String ORDCANCEL = "OrdCancel/";
        /**
         * 社区产品推荐
         */
        public static String ELICIT = "elicit/";

        /**
         * 周边推荐
         */
        public static String AROUND = "around/";

        /**
         * 直供产品搜索
         */
        public static String SUPSEARCH = "SupSearch/";

        /**
         * 账户信息接口
         */
        public static String ACCOUNT = "Account/";

        /**
         * 我的信息
         */
        public static String INFO = "Info/";

        /**
         * 获取收货地址
         */
        public static String GETADDRESS = "Address/";

        /**
         * 保存收货地址
         */
        public static String SAVEADDRESS = "AddressEdit/";

        /**
         * 添加收货地址
         */
        public static String ADDADDRESS = "AddressAdd/";

        /**
         * 默认收货地址
         */
        public static String ADDRESSDEFAULT = "AddressDefault/";

        /**
         * 删除收货地址
         */
        public static String ADDRESSDELETE = "AddressDel/";

        /**
         * 家庭账户管理
         */
        public static String MANAGE = "manage/";

        /**
         * 我的房号
         */
        public static String ROOM = "room/";

        /**
         * 解除房号
         */
        public static String REMOVEROOM = "RemoveRoom/";

        /**
         * 认证房号
         */
        public static String APPROVEROOM = "ApproveRoom/";

        /**
         * 申请认证房号
         */
        public static String APPROOM = "AppRoom/";

        /**
         * 认证家庭成员
         */
        public static String APPMANAGE = "ApproveManage/";

        /**
         * 删除家庭成员
         */
        public static String DELMANAGE = "DelManage/";

        /**
         * 消息内容
         */
        public static String CONTENT = "Content/";

        /**
         * 消息内容
         */
        public static String MESSAGE = "Message/";

        /**
         * 完善信息
         */
        public static String PERFECT = "Perfect/";

        /**
         * 绑定手机
         */
        public static String BIND = "Bind/";

        /**
         * 解除手机
         */
        public static String DELBIND = "DelBind/";

        /**
         * 版本控制
         */
        public static String EDITION = "Edition/";

        /**
         * 反馈
         */
        public static String FEEDBACK = "Feedback/";

        /**
         * 消除红点
         */
        public static String EXTEND = "Extend/";

        public static String CHECK = "Check/";

        public static String EDITROOM = "EditRoom/";
        /**
         * 保修评论
         */
        public static String REP_EVALUATE = "RepEvaluate/";

        /**
         * 保洁评论
         */
        public static String CLEAN_EVALUATE = "CleanEvaluate/";

        /**
         * 订单查询OrderSearch
         */
        public static String ORDER_SERACH = "OrderSearch/";

        /**
         * 我的余额
         */
        public static String ORDER_BALANCE = "Balance/";

        /**
         * 3、获取支付对象
         */
        public static String PAYCHARGE = "PayCharge/";

        /**
         * 4、验证支付结果
         */
        public static String PAYRESPONSE = "PayResponse/";

        /**
         * 历史记录
         */
        public static String ORDER_BALANCE_OLD = "Balance_old/";

        /**
         * 获取手机号Selectphone
         */
        public static String SELECTPHONE = "Selectphone/";

        /**
         * 36派件扫描保存接口Sendscan
         */
        public static String SENDSCAN = "Sendscan/";

        /**
         * 37获取大客户信息Biginfo
         */
        public static String BIGINFO = "Biginfo/";

        /**
         * 37获取大客户信息Biginfo
         */
        public static String Search = "OrderSearch/";

        /**
         * 39、大客户派件Bigdispatch
         */
        public static String BIGDISPATCH = "Bigdispatch/";

        /**
         * 40、更新大客户派件信息Bigdispatchup
         */
        public static String BIGDISPATCHUP = "Bigdispatchup/";

        /**
         * 41、揽收支付
         */
        public static String TAKINGPAY = "Takingpay/";
        /**
         * 42、价格接口
         */
        public static String PRICE = "Price/";

        /**
         * 43、揽收支付状态
         */

        public static String TAKINGPAY_STATUS = "Takingpay/paystatus.php";
        /**
         * 44、用户拒签接口
         */
        public static String REFUSE = "Refuse/";

        /**
         * 45、获取订单
         */

        public static String ORDERMORE = "Ordersign/";

        /**
         * 45、查看用户定时短信接口
         */
        public static String USERMSGTIME = "Usermsgtime/";
        /**
         * 50、更新用户定时短信接口
         */
        public static String USERMSGTIMEUP = "Usermsgtimeup/";
        /**
         * 51、针对用户发送短信接口
         */
        public static String USERMSGSEND = "Usermsgsend/";
        /**
         * 46、众包派送查询是否上架
         */

        public static String SENDDIS = "Senddis/";

        /**
         * 47、众包派送结果提交
         */

        public static String SUBMIT = "Senddis/submitnew.php";

        /**
         * 47-1、众包派送错误信息删除
         */

        public static String ERRORDEL = "Senddis/questiondel.php";

        /**
         * 48、众包派送更新派送信息
         */

        //public static String SCHECK = "Senddis/check.php";
        public static String SCHECK = "Senddis/indexnew.php";


        /**
         * 57、社区账号获取二维码 Senddis
         */
        public static String VCODE = "Senddis/vcode.php";

        /**
         * 57、社区账号获取二维码 Senddis
         */
        public static String SRETURN = "Senddis/return.php";

        /**
         * 58、众包员提交扫描二维码 Senddis
         */
        public static String SUBCODE = "Senddis/subcode.php";


        //众包改版1.0   谁爱更新谁更新
        /**
         * 1.忘记，找回密码
         */
        public static String FINDPASS = "Getpass/";
        /**
         * 2.获取验证码
         */
        public static String GETVERIFY = "Verify/";
        /**
         * 3.修改密码
         */
        public static String CHANGEPASS = "Useruppass/";
        /**
         * 4.派件查询
         */
        public static String PAISELECT = "Senddis/search.php";
        /**
         * 5.获取快递公司
         */
        public static String GetExpress = "GetExpress/index.php";
        /**
         * 6.登录
         */
        public static String ZLOGIN = "Senddis/login.php";

        /**
         * 7.个人中心
         */
        public static String ZUSERINFO = "Senddis/userinfo.php";

        /**
         * 8.派件信息删除
         */
        public static String PAIJIANDEL = "Senddis/del.php";
    }

}
