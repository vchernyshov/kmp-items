Pod::Spec.new do |spec|
    spec.name                     = 'MultiPlatformLibraryItems'
    spec.version                  = '0.0.1-alpha'
    spec.homepage                 = 'https://github.com/vchernyshov/kmp-items'
    spec.source                   = { :git => "https://github.com/vchernyshov/kmp-items.git", :tag => "release/#{spec.version}" }
    spec.authors                  = 'Garage Development'
    spec.license                  = { :type => 'MIT', :file => 'LICENSE' }
    spec.summary                  = 'Swift additions to kmp-items Kotlin/Native library'
    spec.module_name              = "#{spec.name}"
    
    spec.dependency 'MultiPlatformLibrary'

    spec.ios.deployment_target  = '9.0'
    spec.swift_version = '4.2'

    spec.subspec 'core' do |sp|
      sp.source_files = "items/src/iosMain/swift/core/**/*.{h,m,swift}"
    end

    spec.subspec 'diff' do |sp|
      sp.source_files = "items/src/iosMain/swift/diff/**/*.{h,m,swift}"
      sp.dependency 'Differ'
    end

    spec.pod_target_xcconfig = {
        'VALID_ARCHS' => '$(ARCHS_STANDARD_64_BIT)'
    }
end
