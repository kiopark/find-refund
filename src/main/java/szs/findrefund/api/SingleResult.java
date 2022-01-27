package szs.findrefund.api;

import lombok.Getter;

@Getter
public class SingleResult<T> extends CommonResult {
  private T data;

  public void saveData(T data) {
    this.data = data;
  }
}
