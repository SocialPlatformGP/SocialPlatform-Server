package com.example.repository

import com.example.data.models.Post
import com.example.data.models.Tag
import com.example.data.models.User
import com.example.data.requests.CreatePostRequest
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.util.idValue

class PostRepositoryImpl(db:CoroutineDatabase): PostRepository{
    private val postCollection = db.getCollection<Post>()
    private val userCollection = db.getCollection<User>()
    private val tagCollection = db.getCollection<Tag>()
    override suspend fun createPost(postRequest: PostResponse): Boolean {

        return postCollection.insertOne(postRequest.toEntity()).wasAcknowledged()

    }

    override suspend fun updatePost(postRequest: PostResponse): Boolean {
        return postCollection.updateOne(Post::id eq postRequest.id, (postRequest.toEntity())).wasAcknowledged()
    }

    override suspend fun deletePost(postRequest: UpdateOrDeletePostRequest): Boolean {
        return postCollection.deleteOne(Post::id eq postRequest.id).wasAcknowledged()
    }

    override suspend fun getPost(postRequest: UpdateOrDeletePostRequest): PostResponse {
       val post = postCollection.findOne(Post::id eq postRequest.id)
        if(post != null){
            val user = userCollection.findOneById(post.authorID)?:User()
            return post.toResponse()
        }
        else{
            return PostResponse()
        }

    }

    override suspend fun getPosts(): List<PostResponse>
         = postCollection.find().toList()
        .map {
            it.toResponse()
        }

    override suspend fun upvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse {
            val post = postCollection.findOne(Post::id eq postRequest.id)
        if(post != null){
            postCollection.updateOne(Post::id eq postRequest.id, setValue(Post::votes,post.votes+1))
            return post.toResponse()
        }else{
            return PostResponse()
        }
    }

    override suspend fun downvotePost(postRequest: UpdateOrDeletePostRequest): Boolean {
        val post = postCollection.findOne(Post::id eq postRequest.id)
        if(post != null){
            postCollection.updateOne(Post::id eq postRequest.id, setValue(Post::votes,post.votes-1))
            return true
        }else{
            return false
        }
    }

    override suspend fun insertTag(tag: Tag): Boolean {
        val foundTag = tagCollection.findOne(Tag::label eq tag.label)
        if(foundTag != null){
            return true
        }
        return tagCollection.insertOne(tag).wasAcknowledged()
    }

    override suspend fun getTags(): List<Tag> {
        return tagCollection.find().toList()
    }

    override suspend fun getNewPosts(request: LocalDateTime): List<PostResponse> {
        val request1 = request.toInstant(TimeZone.UTC).epochSeconds
        return postCollection.find(Post::lastModified gt request1).toList().map {
            it.toResponse()
        }
    }


}