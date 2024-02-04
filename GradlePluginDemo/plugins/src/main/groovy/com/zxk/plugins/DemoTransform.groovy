package com.zxk.plugins

import com.android.build.api.transform.Context
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider

class DemoTransform extends Transform{
    @Override
    String getName() {
        return "DemoPoint"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
    _transform(transformInvocation.context,transformInvocation.inputs,transformInvocation.outputProvider)
    }

    void _transform(Context context, Collection<TransformInput> inputs, TransformOutputProvider outputProvider)  throws TransformException, InterruptedException, IOException{
        inputs.each {
            it.directoryInputs.each {
                println("directoryInputs.name=${it.name}")
            }
            it.jarInputs.each {
                println("jarInputs.name=${it.name}")
            }
        }
    }
}