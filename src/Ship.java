import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ship {
    private int n;
    private List<PartOfShip> parts;
    public Ship(List<PartOfShip> parts) {
        this.n = parts.size();
        this.parts = parts;
    }
}
