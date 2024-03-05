package com.example.repository

import com.example.data.models.Post
import com.example.data.models.User
import com.example.data.requests.CreatePostRequest
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class PostRepositoryImpl(db:CoroutineDatabase): PostRepository{
    private val postCollection = db.getCollection<Post>()
    private val userCollection = db.getCollection<User>()
    override suspend fun createPost(postRequest: CreatePostRequest): Boolean {
        val user = userCollection.findOne(User::id eq postRequest.userId)
        val post = Post(
            title = postRequest.title,
            body = postRequest.body,
            userId = postRequest.userId
        ).also { it.user = user!!}
        return postCollection.insertOne(post).wasAcknowledged()

    }

    override suspend fun updatePost(postRequest: UpdateOrDeletePostRequest): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postRequest: UpdateOrDeletePostRequest): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(postRequest: UpdateOrDeletePostRequest): PostResponse {
       val post = postCollection.findOne(Post::id eq postRequest.id)
        if(post != null){
            val user = userCollection.findOneById(post.userId)?:User()
            return post.toResponse(user)
        }
        else{
            return PostResponse()
        }

    }

    override suspend fun getPosts(): List<PostResponse>
         = postCollection.find().toList()
        .map {
            it.toResponse(it.user)
        }


}