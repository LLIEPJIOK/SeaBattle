package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private Coordinate coordinate;
    private boolean isHit;
    private List<Coordinate> coordinates;

    public Response(Coordinate coordinate) {
        this.coordinate = coordinate;
        isHit = false;
        coordinates = new ArrayList<>();
    }
}
