package entity;

import modelUtil.Failure;

/** 学生の情報を表すエンティティオブジェクト。 */
public class Student {
  private String id;
  private String name;

  /**
   * 学生の情報を表すエンティティオブジェクト。
   * @param id 学籍番号。8文字以下で、半角英数字またはハイフンのみを含む必要がある。
   * @param name 氏名。64文字以下である必要がある。
   * @throws Failure 制約に反する場合は{@link Failure}型を投げる。
   */
  public Student(String id, String name) throws Failure {
    // 学籍番号と氏名が制約を満たすか確認してからフィールドに割り当てる。
    checkId(id);
    checkName(name);
    this.id = id;
    this.name = name;
  }

  /** 学籍番号を取得する。 */
  public String getId() {
    return this.id;
  }

  /** 氏名を取得する。 */
  public String getName() {
    return this.name;
  }

  /** 氏名を変更する。氏名は64文字以下である必要がある。 */
  public void setName(String name) throws Failure {
    // 新しい氏名が制約を満たすか確認してから、フィールドに新しい氏名を割り当てる。
    checkName(name);
    this.name = name;
  }

  /** 学籍番号が制約を満たすか確認する。 */
  private void checkId(String id) throws Failure {
    if (id.length() > 8 || !id.matches("^[a-zA-Z0-9-]+$")) {
      throw new Failure("学籍番号は8文字以下で、半角英数字またはハイフンのみを含む必要があります。");
    }
  }

  /** 氏名が制約を満たすか確認する。 */
  private void checkName(String name) throws Failure {
    if (name.length() > 64) {
      throw new Failure("氏名は64文字以下である必要があります。");
    }
  }
}
