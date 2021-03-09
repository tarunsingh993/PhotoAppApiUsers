package com.example.photoapp.api.users.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.photoapp.api.users.ui.model.AlbumResponseModel;

//@FeignClient(name = "albums-ws", fallback = AlbumsFallback.class)
@FeignClient(name = "albums-ws", fallbackFactory = AlbumsFallback.class)
public interface AlbumsServiceClient {

	@GetMapping(path = "/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}


@Component
class AlbumsFallback implements FallbackFactory<AlbumsServiceClient> {

	@Override
	public AlbumsServiceClient create(Throwable cause) {
		// pass the throwable in the new constructor
		return new AlbumsServiceClientFallback();
	}

}

class AlbumsServiceClientFallback implements AlbumsServiceClient {

	//create a logger and contructor that takes throwable
	
	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		// log the exceptions
		return new ArrayList<AlbumResponseModel>();// also provide default values
	}

}

//@Component
//class AlbumsFallback implements AlbumsServiceClient {
//
//	@Override
//	public List<AlbumResponseModel> getAlbums(String id) {
//		return new ArrayList<AlbumResponseModel>();// also provide default values
//	}
//
//}