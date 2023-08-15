package club.ovelya.socketsystem.utils;

public class HttpStatusUtils {

  /**
   * 操作成功
   */
  public static String SUCCESS = "200";

  /**
   * 对象创建成功
   */
  public static String CREATED = "201";

  /**
   * 请求已经被接受
   */
  public static String ACCEPTED = "202";

  /**
   * 操作已经执行成功，但是没有返回数据
   */
  public static String NO_CONTENT = "204";

  /**
   * 资源已被移除
   */
  public static String MOVED_PERM = "301";

  /**
   * 重定向
   */
  public static String SEE_OTHER = "303";

  /**
   * 资源没有被修改
   */
  public static String NOT_MODIFIED = "304";

  /**
   * 参数列表错误（缺少，格式不匹配）
   */
  public static String BAD_REQUEST = "400";

  /**
   * 未授权
   */
  public static String UNAUTHORIZED = "401";

  /**
   * 访问受限，授权过期
   */
  public static String FORBIDDEN = "403";

  /**
   * 资源，服务未找到
   */
  public static String NOT_FOUND = "404";

  /**
   * 不允许的http方法
   */
  public static String BAD_METHOD = "405";

  /**
   * 资源冲突，或者资源被锁
   */
  public static String CONFLICT = "409";

  /**
   * 不支持的数据，媒体类型
   */
  public static String UNSUPPORTED_TYPE = "415";

  /**
   * 系统内部错误
   */
  public static String ERROR = "500";

  /**
   * 接口未实现
   */
  public static String NOT_IMPLEMENTED = "501";
}
