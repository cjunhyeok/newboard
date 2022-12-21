package boardex.newboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCondition {

    private String nickName;
    private String title;
    private Long page;
}
