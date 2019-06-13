package son.minho.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
	int id;
	String title;
	int audience;
	String posterUrl;
	String description;
	int genreId;
}
