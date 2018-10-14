package com.github.eunsiljo.arcoresample

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    companion object {
        private const val MIN_BUILD_VERSION_CODE: Int = Build.VERSION_CODES.N
        private const val MIN_OPENGL_VERSION: Double = 3.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        if (!checkIsSupportedDevice()) {
            finish()
        }

        with(list) {
            adapter = ArrayAdapter<CharSequence>(
                context,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                ListItem.values().map { it.title }
            )
            onItemClickListener = this@MainActivity
        }
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            ListItem.OVERVIEW.ordinal -> {
                startActivity(Intent(this@MainActivity, OverviewActivity::class.java))
            }
            ListItem.CREATE_RENDERABLES.ordinal -> {
                startActivity(Intent(this@MainActivity, CreateRenderablesActivity::class.java))
            }
            ListItem.BUILD_THE_SCENE.ordinal -> {

            }
            ListItem.LETS_PLAY_YOGA.ordinal -> {

            }
        }
    }

    private fun checkIsSupportedDevice(): Boolean =
        when (Build.VERSION.SDK_INT < MIN_BUILD_VERSION_CODE) {
            true -> {
                Toast.makeText(this, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show()
                false
            }
            false -> {
                val openGlVersionString = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                    .deviceConfigurationInfo
                    .glEsVersion
                when (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
                    true -> {
                        Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show()
                        false
                    }
                    false -> true
                }
            }
        }

    enum class ListItem(val title: String) {
        OVERVIEW("1. Overview"),
        CREATE_RENDERABLES("2. Create Renderables"),
        BUILD_THE_SCENE("3. Build the Scene"),
        LETS_PLAY_YOGA("4. Let's play Yoga")
    }

    /*
    companion object {
        private const val ANDY_ASSET = "andy.sfb"
        private const val DUCK_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf"
    }

    private val arFragment: ArFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_ar) as ArFragment
    }

    private val textViewState: CompletableFuture<ViewRenderable> by lazy {
        ViewRenderable.builder().setView(this, R.layout.view_ar_text).build()
    }
    private val imageViewState: CompletableFuture<ViewRenderable> by lazy {
        ViewRenderable.builder().setView(this, R.layout.view_ar_image).build()
    }
    private val andyState: CompletableFuture<ModelRenderable> by lazy {
        ModelRenderable.builder().setSource(this, Uri.parse(ANDY_ASSET)).build()
    }
    private val materialState: CompletableFuture<Material> by lazy {
        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.MAGENTA))
    }
    private val duckState: CompletableFuture<ModelRenderable> by lazy {
        ModelRenderable.builder()
            .setSource(this, RenderableSource.builder().setSource(
                this,
                Uri.parse(DUCK_ASSET),
                RenderableSource.SourceType.GLTF2)
                .setScale(0.2f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build()
            )
            .setRegistryId(DUCK_ASSET)
            .build()
    }

    private var viewRenderable: ViewRenderable? = null
    private var modelRenderable: ModelRenderable? = null

    private var textViewRenderable: ViewRenderable? = null
    private var imageViewRenderable: ViewRenderable? = null
    private var andyRenderable: ModelRenderable? = null
    private var sphereRenderable: ModelRenderable? = null
    private var cubeRenderable: ModelRenderable? = null
    private var cylinderRenderable: ModelRenderable? = null
    private var duckRenderable: ModelRenderable? = null

    private val viewNode
        get() = viewRenderable?.let { Node().apply { renderable = it } }
    private val modelNode
        get() = modelRenderable?.let { Node().apply { renderable = it } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIsSupportedDevice()) {
            finish()
        }
        setContentView(R.layout.activity_ar)

        setAllRenderables()
        arFragment.setOnTapArPlaneListener(this)
    }

    private fun setAllRenderables() {
        CompletableFuture.allOf(
            textViewState,
            imageViewState,
            andyState,
            materialState,
            duckState
        ).handle { _, throwable ->
            when (throwable == null) {
                true -> {
                    textViewRenderable = textViewState.get()
                    imageViewRenderable = imageViewState.get()
                    andyRenderable = andyState.get()
                    sphereRenderable =
                        makeMaterialModelRenderable(ARShapeType.SPHERE, materialState.get())
                    cubeRenderable =
                        makeMaterialModelRenderable(ARShapeType.CUBE, materialState.get())
                    cylinderRenderable =
                        makeMaterialModelRenderable(ARShapeType.CYLINDER, materialState.get())
                    duckRenderable = duckState.get()
                }
                false -> {
                    Toast.makeText(this, "Unable to load renderables", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val anchorNode = AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
            addChild(createBaseNode())
        }

        andyRenderable?.let {
            TransformableNode(arFragment.transformationSystem).apply {
                setParent(anchorNode)
                renderable = it.apply {
                    localScale = Vector3(0.2f, 0.2f, 0.2f)
                    select()
                }
            }
        }
    }

    private fun createBaseNode(): Node = Node().apply {
        val baseNode = this
        textViewRenderable?.let {
            Node().apply {
                setParent(baseNode)
                renderable = it.apply {
                    localPosition = Vector3(-0.2f, -0.2f, -0.2f)
                }
            }
        }

        imageViewRenderable?.let {
            Node().apply {
                setParent(baseNode)
                renderable = it.apply {
                    localPosition = Vector3(0.2f, 0.2f, 0.2f)
                }
            }
        }
    }

    private fun setViewRenderable(viewState: CompletableFuture<ViewRenderable>) {
        viewState
            .thenAccept { renderable -> viewRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load view renderable", Toast.LENGTH_LONG).show()
                null
            }
    }

    private fun setModelRenderable(modelState: CompletableFuture<ModelRenderable>) {
        modelState
            .thenAccept { renderable -> modelRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load model renderable", Toast.LENGTH_LONG).show()
                null
            }
    }

    private fun setMaterialRenderable(
        materialState: CompletableFuture<Material>,
        shapeType: ARShapeType
    ) {
        materialState
            .thenAccept { material ->
                modelRenderable = makeMaterialModelRenderable(shapeType, material)
            }
            .exceptionally {
                Toast.makeText(this, "Unable to load material renderable", Toast.LENGTH_LONG).show()
                null
            }
    }

    private fun makeMaterialModelRenderable(
        shapeType: ARShapeType,
        material: Material
    ): ModelRenderable =
        when (shapeType) {
            ARShapeType.SPHERE -> {
                ShapeFactory.makeSphere(
                    0.1f,
                    Vector3(0.0f, 0.15f, 0.0f),
                    material.apply {
                        setFloat3(
                            MaterialFactory.MATERIAL_COLOR,
                            Color(android.graphics.Color.RED)
                        )
                        setFloat(MaterialFactory.MATERIAL_METALLIC, 1f)
                    }
                )
            }
            ARShapeType.CUBE -> {
                ShapeFactory.makeCube(
                    Vector3(0.2f, 0.2f, 0.2f),
                    Vector3(0.0f, 0.15f, 0.0f),
                    material.apply {
                        setFloat3(
                            MaterialFactory.MATERIAL_COLOR,
                            Color(android.graphics.Color.BLUE)
                        )
                        setFloat(MaterialFactory.MATERIAL_ROUGHNESS, 1f)
                    }
                )
            }
            ARShapeType.CYLINDER -> {
                ShapeFactory.makeCylinder(
                    0.1f,
                    0.3f,
                    Vector3(0.0f, 0.15f, 0.0f),
                    material.apply {
                        setFloat3(
                            MaterialFactory.MATERIAL_COLOR,
                            Color(android.graphics.Color.GREEN)
                        )
                        setFloat(MaterialFactory.MATERIAL_REFLECTANCE, 1f)
                    }
                )
            }
        }
        */
}
